#!/bin/bash
echo "Run Repository"
rm -r ../out/outR;
mkdir ../out/outR;
javac -d "../out/outR" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outR" MainProgram.SharedRegionMain Repository