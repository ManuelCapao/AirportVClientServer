#!/bin/bash
echo "Run Arrival Terminal Exit"
rm -r ../out/outATE;
mkdir ../out/outATE;
javac -d "../out/outATE" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outATE" MainProgram.SharedRegionMain ArvTerminalExit