# Centralizaed configuration demo
## About
This project serves as example how to use Spring Centralized Configuration both as Config Server and Config Client.

## Git configuration repository
For simplicity, this project uses same git repository for demo application and as location for configuration files. You can however use any git repository as storage for configuration.

Configuartion files should be named in convention ```{application-name}-{profile}.{extension}```.

For example: ```client1-prod.properties```

In this project we have following structure:

```
Application:          Application:
client1               client2
└-profile: prod       └-profile: prod
└-profile: test       └-profile: test
```

So our configuration files will be as followed:

```
client1-prod.properties
client1-test.properties
client2-prod.properties
client2-test.properties
```

## Server
Config Server is simple Spring Application annotated with ```@EnableConfigServer```

```
@SpringBootApplication
@EnableConfigServer
class CentralConfigServerApplication

fun main(args: Array<String>) {
	runApplication<CentralConfigServerApplication>(*args)
}
```
This example uses files located on public github repository using location and credentials used in ```application.properties``` file
```
spring.cloud.config.server.git.uri=https://github.com/MichaelSzychulski/central-config
```

This repository is public, so it doesn't need credentials. We can access private repositories by providing credentials.
```
spring.cloud.config.server.git.username=username
spring.cloud.config.server.git.password=password
```

Default pathing will be looking for files in root folder. To get properties from different folder, use ```search-paths``` property
```
spring.cloud.config.server.git.search-paths=properties/{profile}
```
This pathing will look for properties in subfolder ```properties/{profile}```, where ```{profile}``` plcaeholder is our client application active profile.
For more info about ```search-paths``` placeholders and more, see: https://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.3.2.RELEASE/#_spring_cloud_config_server 

We can test now our server by calling on one of auto generated endpoints.

```
$> curl http://localhost:8888/client1/prod/main
{config server host}/{client name}/{profile}/{label(git branch)}
```

More info about possible endpoints: https://www.baeldung.com/spring-cloud-configuration#Querying


## Client
### Configuration
Config Client Spring Application require configuration on application.properties
```
spring.application.name=client1
spring.config.import=optional:configserver:http://localhost:8888

#Change Environment Variables to see different configurations being loaded based on:
##ACTIVE_PROFILE - prod/test
spring.profiles.active=${ACTIVE_PROFILE:prod}
##LABEL (git branch) - main/develop
spring.cloud.config.label=${LABEL:main}
```

```spring.application.name``` and ```spring.profiles.active``` - are values used by server to decide which config file should be returned

```spring.cloud.config.label``` - label of our git branch

For given configuration and no Environment Variables set, server will return file ```client1-prod.properties``` from branch ```main```

### Example
To show that configurations are properly set we are using simple Controller to return values. Remember to first run config server.
```
@RestController
class TestController {

    @Value("\${example.animal}")
    lateinit var animal: String

    @Value("\${example.fruit}")
    lateinit var fruit: String

    @Value("\${example.branch}")
    lateinit var branch: String

    @Value("\${example.profile}")
    lateinit var profile: String

    @GetMapping("/config")
    fun getConfig(): Map<String, String> {
        return mapOf(
            "animal" to animal,
            "fruit" to fruit,
            "branch" to branch,
            "profile" to profile
        )
    }
}
```
```
$> curl http://localhost:8881/config
{"animal":"dog","fruit":"apple","branch":"main","profile":"prod"}

```


