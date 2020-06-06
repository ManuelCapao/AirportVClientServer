#!/bin/bash
echo "Run Departure Transfer Quay"
rm -r ../out/outDTQ;
mkdir ../out/outDTQ;
javac -d "../out/outDTQ" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outDTQ" MainProgram.SharedRegionMain DepTransferQuay