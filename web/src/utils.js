const getXsrfToken = () => {
  const token = document.cookie
    .split('; ')
    .find(row => row.startsWith('XSRF-TOKEN='));
  return token ? token.split('=')[1] : null;
}

const GAME_VERSION_INFO = {
  "SMBX_38A_145": {
    name: "SMBX-38A 1.4.5",
    icon: "/smbx-38a.png"
  },
  "SMBX_38A_144": {
    name: "SMBX-38A 1.4.4",
    icon: "/smbx-38a.png"
  },
  "SMBX_38A_OTHERS": {
    name: "SMBX-38A 早期版本",
    icon: "/smbx-38a.png"
  },
  "SMBX_2_0": {
    name: "SMBX 2.0",
    icon: "/smbx2.png"
  },
  "SMBX_1_3": {
    name: "SMBX 1.3",
    icon: "/smbx-legacy.png"
  },
  "THEXTECH": {
    name: "TheXTech",
    icon: "/thextech.png"
  },
  "UNKNOWN": {
    name: "未知版本",
    icon: "/unknown-version.png"
  },
}

const DOWNLOAD_PROVIDER_INFO = {
  "DIRECT_LINK": {
    name: "下载",
    icon: "/direct-link.png",
    enabled: true
  },
  "THIRD_PARTY_WEBPAGE": {
    name: "跳转到第三方网页下载",
    icon: "/third-party.png",
    enabled: true
  },
  "UNKNOWN": {
    name: "不提供下载",
    icon: "/unknown-download.png",
    enabled: false
  }
}

export {
  getXsrfToken,
  GAME_VERSION_INFO,
  DOWNLOAD_PROVIDER_INFO
}
