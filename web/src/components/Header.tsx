import {Image, Text} from "@fluentui/react-components";

const Header = () => {
  return <>
    <div className="flex-row header">
      <span className="flex-row">
      <Image src="/smbx-world.png" alt="SMBX World Title"/>
      <Text className="text-lift" size={600} weight="semibold">地图存档下载</Text>
      </span>
    </div>
  </>
}

export default Header
