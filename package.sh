#!/bin/bash

buildDir="./labs-spring-mvc"
outputName="labs-spring-mvc"

mvn -f ./pom.xml clean install -Dmaven.test.skip
mvn -f ./pom.xml dependency:copy-dependencies -Dmdep.useRepositoryLayout=true
mvn -f ./pom.xml dependency:copy-dependencies -Dclassifier=sources -Dmdep.failOnMissingClassifierArtifact=false -Dmdep.useRepositoryLayout=true

rm -rf $buildDir
mkdir $buildDir
mkdir "$buildDir"/repo

for file in $(ls -d ./*/)
do
  directory="$file"target/dependency
  test -e $directory
#  printf "$file $?\n"
  if [ "$?" -eq 0 ]
  then
    cp -r "$file"target/dependency/* "$buildDir"/repo/
  fi
done

mvn -f ./pom.xml clean
mkdir "$buildDir"/projects
cp -r ./spring-* "$buildDir"/projects
cp -r ./out-of-container-test-* "$buildDir"/projects
cp ./pom.xml "$buildDir"/projects
cp ./settings.xml "$buildDir"
mvn -f "$buildDir"/projects/pom.xml eclipse:eclipse

rm -f "$outputName".zip
zip -qr $outputName "$buildDir"/*
rm -rf $buildDir
