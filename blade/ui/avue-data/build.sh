#!/bin/bash 

echo "打包文件"
yarn build
echo "传输文件"

scp -P 35738 -r ./dist/** root@8.130.12.92:/back/avue/avue/avue-cli

echo "部署成功"