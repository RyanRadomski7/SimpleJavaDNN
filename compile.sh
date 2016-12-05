#!/bin/bash
mkdir bin
javac src/* -d bin
jar cvfm IrisDemo.jar manifest/manifestIris bin/
echo -e "#!/bin/bash\njava -jar IrisDemo.jar" > irisDemo.sh
chmod +x irisDemo.sh
jar cvfm XORDemo.jar manifest/manifestXOR bin/
echo -e "#!/bin/bash\njava -jar XORDemo.jar" > xorDemo.sh
chmod +x xorDemo.sh
echo "finished compiling run irisDemo.sh to run the iris demo or xorDemo.sh to run the xor demo"
