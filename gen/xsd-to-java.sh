#!/bin/sh

rm -rf src
mkdir src

# /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/xjc
xjc -XautoNameResolution -p io.vpv.saml.metadata.modal.v2 -d src saml-schema-metadata-2.0.xsd