#!/usr/bin/env bash

sed -i '' 's/smallrye-parent/smallrye-jakarta-parent/g' pom.xml

find . -type f -name '*.java' -exec sed -i '' 's/javax./jakarta./g' {} +

mvn build-helper:parse-version versions:set -DnewVersion=2.1.0-SNAPSHOT
mvn -ntp versions:set-property -Dproperty=version.smallrye.common -DnewVersion=2.0.0

mvn -ntp versions:set-property -Dproperty=version.testng -DnewVersion=7.6.0

mvn -ntp versions:set-property -Dproperty=version.weld.api -DnewVersion=4.0.SP1
mvn -ntp versions:set-property -Dproperty=version.weld.core -DnewVersion=4.0.3.Final
mvn -ntp versions:set-property -Dproperty=version.weld.junit -DnewVersion=3.0.0.Final

mvn -ntp versions:set-property -Dproperty=version.arquillian -DnewVersion=1.7.0.Alpha13
mvn -ntp versions:set-property -Dproperty=version.arquillian.weld-embedded -DnewVersion=3.0.1.Final
mvn -ntp versions:set-property -Dproperty=version.arquillian.wildfly -DnewVersion=5.0.0.Alpha2
mvn -ntp versions:set-property -Dproperty=version.arquillian.jetty -DnewVersion=1.0.0.Final
mvn -ntp versions:set-property -Dproperty=artifactId.arquillian.jetty -DnewVersion=arquillian-jetty-embedded-11
mvn -ntp versions:set-property -Dproperty=version.jetty -DnewVersion=11.0.7

mvn -ntp versions:set-property -Dproperty=version.jakarta.annotation -DnewVersion=2.0.0
mvn -ntp versions:set-property -Dproperty=version.jakarta.cdi -DnewVersion=3.0.0
mvn -ntp versions:set-property -Dproperty=version.jakarta.ws-rs -DnewVersion=3.0.0
mvn -ntp versions:set-property -Dproperty=version.jakarta.jsonp -DnewVersion=2.0.0
mvn -ntp versions:set-property -Dproperty=version.jakarta.jsonb -DnewVersion=2.0.0
mvn -ntp versions:set-property -Dproperty=version.weld -DnewVersion=4.0.3.Final
mvn -ntp versions:set-property -Dproperty=version.resteasy -DnewVersion=6.0.0.Final
