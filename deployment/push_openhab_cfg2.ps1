#Write-Host "Sending upgrade command to $dev"
$remote_session = "pi"
$remote_host = "raspberrypi"

$local_path = "..\openhab\conf"
$remote_path = "\\RASPBERRYPI\openhab\conf"

$files = @(
	"services\addons.cfg",
	"services\mqtt.cfg",
	"services\mqttitude.cfg",
	"services\rrd4j.cfg",	
	"services\xbmc.cfg",
	"services\yamahareceiver.cfg",
	"transform\presence_pl.map",
	"items\korytarz.items",
	"items\presence.items",
	"items\proto.items",
	"items\salon.items",
	"items\sypialnia.items",
	"sitemaps\_default.sitemap",
	"sitemaps\proto.sitemap",
	"persistence\rrd4j.persist",
	"rules\mieszkanie.rules",
	"rules\salon_sufit.rules",
	"rules\mieszkanie.rules"
)

$src_path = $remote_path
$dest_path = $local_path

foreach ($file in $files) {
	$src = "$src_path\$file"
	$dest = "$dest_path\$file"
	
	Write-Host "Copying $src to $dest"
	Copy-Item $src $dest
}

