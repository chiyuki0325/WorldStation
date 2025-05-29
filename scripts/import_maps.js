const fs = require("fs")
const alist = "https://assets2.station.chyk.ink"
const rootPath = "/station"

const GAME_VERSIONS = {
    UNKNOWN: 0,
    SMBX_38A_145: 1,
    SMBX_38A_144: 2,
    SMBX_38A_OTHERS: 3,
    SMBX_2_0: 4,
    SMBX_1_3: 5,
    SMBX_THEXTECH: 6,
}

const UPLOADER = 6
const DIRECT_LINK = 1

// 在文件名开头提取版本号
const GAME_VERSION_REGEX = /^\[([0-9a-zA-Z.]+)\]/

const getFsList = (path) => fetch(`${alist}/api/fs/list`, {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        page: 1,
        per_page: 300,
        password: "",
        refresh: false,
        path
    })
}).then(res => res.json()).then(j => j.data.content?.map(c => c.name))

const sql = []

const processAll = getFsList(rootPath).then(list => {
    return Promise.all(
        list.map(folder => getFsList(rootPath + "/" + folder).then(files => {
            if (files) {
                files.forEach(file => {
                    // 文件名例如 "[1.3] The Invasion 2.zip"
                    const versionMatch = file.match(GAME_VERSION_REGEX)
                    let version = GAME_VERSIONS.UNKNOWN
                    console.log(`Processing file: ${file}`)
                    if (versionMatch) {
                        const versionString = versionMatch[1]
                        if (versionString.toLowerCase().includes("thextech")) {
                            version = GAME_VERSIONS.SMBX_THEXTECH
                        } else if (versionString === "1.3") {
                            version = GAME_VERSIONS.SMBX_1_3
                        } else if (versionString === "1.4.4") {
                            version = GAME_VERSIONS.SMBX_38A_144
                        } else if (versionString === "1.4.5") {
                            version = GAME_VERSIONS.SMBX_38A_145
                        } else if (versionString.startsWith("1.4")) {
                            version = GAME_VERSIONS.SMBX_38A_OTHERS
                        } else if (versionString.startsWith("2")) {
                            version = GAME_VERSIONS.SMBX_2_0
                        }
                        console.log(`Detected version: ${versionString} (${version})`)
                    }
                    const filePath = `${rootPath}/${folder}/${file}`
                    const url = `${alist}/d/${encodeURIComponent(filePath)}`.replace(/'/g, "%27") // 处理单引号
                    let title = file.replace(/\.zip$/, "").replace(/\.rar$/, "").replace(/\.7z$/, "")
                        .replace(/\[.*?\]/g, "").trim()
                        .replace(/'/g, "''") // 处理含有单引号的情况
                    if ((!title.includes(" ")) && title.includes("_")) {
                        title = title.replace(/_/g, " ")
                    }
                    sql.push(
                        `INSERT INTO maps (title, title_lower, uploader, game_version, download_provider, download_url) VALUES ('${title}', '${title.toLowerCase()}', ${UPLOADER}, ${version}, ${DIRECT_LINK}, '${url}');\n`
                    )
                })
            } else {
                console.log(`No files found in folder: ${folder}`)
            }
        }))
    )
})

processAll.then(() => {
    fs.writeFileSync("output.sql", sql.join(""), "utf8");
    console.log("SQL 已写入 output.sql");
});