# Native central configuration
Normally, central configuration does not support placeholders from Environment Variables in configuration files for clients. However, using ```spring.cloud.config.server.overrides``` property in server's own configuration, we can use placeholder values to forcefully override values in clients configurations.

```
spring:
  cloud:
    config:
      server:
        overrides:
          example:
            animal: ${ANIMAL}
```
This will force all clients to set ```example.animal``` value to this specified in server's ```ANIMAL``` EnvVar. 

But that's not ideal, clients may want to use their own values. With EnvVars on server side:
```
ANIMAL=zebra
ds=$
```
we can change our server config to look like this:
```
spring:
  cloud:
    config:
      server:
        overrides:
          example:
            animal: ${ds}{ANIMAL:${ANIMAL}}
```
which will effectively set all clients ```example.animal``` value to ```${ANIMAL:zebra}```. If client sets his own ```ANIMAL``` EnvVar it will be respected, and if not, default server value is set.
