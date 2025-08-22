const getXsrfToken = () => {
  const token = document.cookie
    .split('; ')
    .find(row => row.startsWith('XSRF-TOKEN='));
  return token ? token.split('=')[1] : null;
}

const GAME_VERSION_INFO = {
  "SMBX_38A_145": {
    name: "SMBX-38A 1.4.5",
    short: "1.4.5",
    icon: "/static/smbx-38a.png"
  },
  "SMBX_38A_144": {
    name: "SMBX-38A 1.4.4",
    short: "1.4.4",
    icon: "/static/smbx-38a.png"
  },
  "SMBX_38A_OTHERS": {
    name: "SMBX-38A 早期版本",
    short: "1.4.x",
    icon: "/static/smbx-38a.png"
  },
  "SMBX_2_0": {
    name: "SMBX 2.0",
    short: "2.0",
    icon: "/static/smbx2.png"
  },
  "SMBX_1_3": {
    name: "SMBX 1.3",
    short: "1.3",
    icon: "/static/smbx-legacy.png"
  },
  "SMBX_THEXTECH": {
    name: "TheXTech",
    short: "TheXTech",
    icon: "/static/thextech.png"
  },
  "UNKNOWN": {
    name: "未知版本",
    short: "未知版本",
    icon: "/static/unknown-version.png"
  },
}

const DOWNLOAD_PROVIDER_INFO = {
  "DIRECT_LINK": {
    name: "下载",
    icon: "/static/direct-link.png",
    enabled: true
  },
  "THIRD_PARTY_WEBPAGE": {
    name: "跳转到第三方网页下载",
    icon: "/static/third-party.png",
    enabled: true
  },
  "UNKNOWN": {
    name: "不提供下载",
    icon: "/static/unknown-download.png",
    enabled: false
  }
}

async function uploadFile(file, fileName, uploadKind, onProgress) {
  return new Promise((resolve, reject) => {
    const xhr = new XMLHttpRequest();

    const filename = encodeURIComponent(fileName);

    xhr.open('PUT', `/api/onedrive/upload?upload_kind=${uploadKind}&file_name=${filename}`, true)

    const xsrfToken = getXsrfToken()

    // 设置自定义头部
    xhr.setRequestHeader('Content-Type', file.type || 'application/octet-stream');
    xhr.setRequestHeader('X-XSRF-TOKEN', xsrfToken)

    // 进度处理
    xhr.upload.onprogress = (event) => {
      if (event.lengthComputable) {
        const percentComplete = Math.round((event.loaded / event.total) * 100);
        if (onProgress) {
          onProgress(percentComplete);
        }
      }
    };

    xhr.onload = () => {
      if (xhr.status >= 200 && xhr.status < 300) {
        resolve({
          ok: true,
          status: xhr.status,
          statusText: xhr.statusText,
          text: () => Promise.resolve(xhr.responseText),
          json: () => {
            try {
              return Promise.resolve(JSON.parse(xhr.responseText));
            } catch (e) {
              return Promise.reject(new Error('解析JSON失败'));
            }
          }
        });
      } else {
        resolve({
          ok: false,
          status: xhr.status,
          statusText: xhr.statusText,
          text: () => Promise.resolve(xhr.responseText),
          json: () => {
            try {
              return Promise.resolve(JSON.parse(xhr.responseText));
            } catch (e) {
              return Promise.reject(new Error('解析JSON失败'));
            }
          }
        });
      }
    };

    xhr.onerror = () => {
      reject(new Error('上传请求失败'));
    };

    // 发送文件
    xhr.send(file);
  });
}

export {
  getXsrfToken,
  GAME_VERSION_INFO,
  DOWNLOAD_PROVIDER_INFO,
  uploadFile
}
