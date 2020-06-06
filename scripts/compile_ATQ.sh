#!/bin/bash
echo "Run Arrival Transfer Quay"
rm -r ../out/outATQ;
mkdir ../out/outATQ;
javac -d "../out/outATQ" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outATQ" MainProgram.SharedRegionMain ArvTransferQuay