#Write-Host "Sending upgrade command to $dev"
$remote_session = "pi"
$remote_host = "raspberrypi"

$local_path = "..\openhab\conf"
$remote_path = "control\openhab\conf"
#$remote_path = "\etc\openhab2"

$files = @(
	"services\addons.cfg",
	"services\mqtt.cfg",
	"services\mqttitude.cfg",
	"services\yamahareceiver.cfg",
	"services\xbmc.cfg",
	"transform\presence_pl.map",
	"items\salon.items",
	"items\presence.items",
	"items\proto.items",
	"items\korytarz.items",
	"sitemaps\_default.sitemap",
	"sitemaps\proto.sitemap",
	"rules\mieszkanie.rules",
	"rules\salon_sufit.rules",
	"rules\swieta_bozego_narodzenia.rules"
)


$files = @(
	#"items\salon.items",
	#"rules\salon_sufit.rules",
	#"rules\mieszkanie.rules"
	"items\korytarz.items",
	"sitemaps\_default.sitemap"
	#"sitemaps\_default.sitemap"
)

foreach ($file in $files) {
	$remote_file = "$remote_path\$file".replace("\", "/")
	& "pscp" -load $remote_session "$($local_path)\$file" "$($remote_host):$remote_file"
}

