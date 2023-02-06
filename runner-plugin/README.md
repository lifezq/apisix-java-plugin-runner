## apisix java 插件使用说明

### 1. 安装启动apisix

```shell
git clone https://github.com/apache/apisix-docker.git
cd apisix-docker/example
docker-compose -p docker-apisix up -d
```

### 2. 修改配置

2.1 修改apisix_conf目录下 config.yaml 在最后一行后追加如下内容

```yaml
ext-plugin: # this is the new line that you have to do
  cmd: [ '/usr/local/jdk-11/bin/java', '-jar', '-Xmx1g', '-Xms1g', '/usr/local/apisix/apisix-runner-bin/apisix-java-plugin-runner.jar' ]

```

2.2 修改apisix启动配置    
添加java plugin目录挂载   
添加jdk目录挂载

```yaml
    volumes:
      - ./my_jdk_conf/openjdk-11_linux-x64_bin/jdk-11:/usr/local/jdk-11
      - ./plugins/apisix-java-plugin-runner-0.4.0/dist/apisix-runner-bin:/usr/local/apisix/apisix-runner-bin
```

2.3 替换runner-core中pom.xml的apisix-runner-plugin-sdk为apisix-runner-plugin，以使插件生效

### 3. 重启apisix

### 4. 编写java 插件

在runner-plugin下编写自己的java插件即可

### 5. 打包java插件

在apisix-java-plugin-runner根目录下，运行如下命令

```shell
mvn package
```

编译完成后，会在dist目录下生成两个文件

```shell
$ ls dist/
apache-apisix-java-plugin-runner-0.4.0-bin.tar.gz  apisix-java-plugin-runner-0.4.0-src.tgz
```

进入dist目录，解压目标文件

```shell
cd dist
tar -zxvf apache-apisix-java-plugin-runner-0.4.0-bin.tar.gz
```

### 6. 重启apisix

检查所有配置无误后，再次重启apisix，此时编写的插件就已生效啦！~