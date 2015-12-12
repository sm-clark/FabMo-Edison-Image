DESCRIPTION="The FabMo Updater Service"
LICENSE = "Apache-2.0"
 
#SRC_URI = "git://github.com/FabMo/FabMo-Updater.git;protocol=https"
#SRCREV = "${AUTOREV}"

DEPENDS = "dbus-glib expat"
RDEPENDS_${PN} = "git bash nodejs-npm"

LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

S = "${WORKDIR}/git"

inherit npm

NPM_INSTALL_FLAGS += " --build-from-source"

do_fetch() {
	git clone https://github.com/FabMo/FabMo-Updater.git ${S} --depth=1
}

do_compile() {
    oe_runnpm install
}

do_install() {
    install -d ${D}/opt/fabmo
    install -d ${D}${systemd_unitdir}/system
    #mv ${S}/node_modules/serialport/build/serialport/v1.7.4/Release/node-v11-linux-i586 ${S}/node_modules/serialport/build/serialport/v1.7.4/Release/node-v11-linux-ia32
    cp -r ${S} ${D}/fabmo/updater
    install -m 0644 ${S}/files/fabmo-updater.service ${D}${systemd_unitdir}/system/
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo-updater.service"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr"

PACKAGES = "${PN}"