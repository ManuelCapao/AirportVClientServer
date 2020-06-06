#!/bin/bash

echo "Build And Deploy"
chmod +x ../scripts/compile_AL.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_AL.sh'



chmod +x ../scripts/compile_ATE.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_ATE.sh'

chmod +x ../scripts/compile_ATQ.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_ATQ.sh'

chmod +x ../scripts/compile_BCP.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_BCP.sh'


chmod +x ../scripts/compile_BRO.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_BRO.sh'


chmod +x ../scripts/compile_DTE.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_DTE.sh'

chmod +x ../scripts/compile_DTQ.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_DTQ.sh'



chmod +x ../scripts/compile_R.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_R.sh'

chmod +x ../scripts/compile_RM.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_RM.sh'


chmod +x ../scripts/compile_SR.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_SR.sh'


chmod +x ../scripts/compile_ASC.sh
gnome-terminal -x /bin/bash -c './../scripts/compile_ASC.sh'
