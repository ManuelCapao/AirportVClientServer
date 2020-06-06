#!/bin/bash
echo "Run Repository Main"
rm -r ../out/outRM;
mkdir ../out/outRM;
javac -d "../out/outRM" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/RepositoryMain.java
java -classpath "../out/outRM" MainProgram.RepositoryMain