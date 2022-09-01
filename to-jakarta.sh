#!/usr/bin/env bash

sed -i '' 's/smallrye-parent/smallrye-jakarta-parent/g' pom.xml

find . -type f -name '*.java' -exec sed -i '' 's/javax./jakarta./g' {} +

mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.nextMajorVersion}.0.0-SNAPSHOT
mvn -ntp versions:update-property -Dproperty=version.smallrye.common -DnewVersion=2.0.0-RC1

