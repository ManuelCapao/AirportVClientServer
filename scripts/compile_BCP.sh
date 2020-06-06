#!/bin/bash
echo "Run Baggage Collection Point"
rm -r ../out/outBCP;
mkdir ../out/outBCP;
javac -d "../out/outBCP" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outBCP" MainProgram.SharedRegionMain BgCollectionPoint