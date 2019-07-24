let config =  /usr/local/var/dhall-kubernetes/api/Deployment/default
  ⫽ { name = "storage-manager"
  , hostAliases = /env/hostaliases
  , secretVolumes = ./secretVolumes
  , pathVolumes = [{name = "krb5conf", path = "/etc/krb5.conf"}]
  , configMapVolumes = [{name = "config-volume", configMap = "storage-manager-conf"}]
  , persistentVolumes = [{name = "glusterfsvol", claimName = "gluster-claim"}]
  , containers =
                [   /usr/local/var/dhall-kubernetes/api/Deployment/defaultContainer
                  ⫽ /env/nexus
                  ⫽ { name = "storage-manager"
                    , imageName = "daf-storage-manager"
                    , imageTag = "1.0.6.9"
                    , port = [ 9000 ] : Optional Natural
                    , mounts = ./volumeMounts
                    , simpleEnvVars = [ { mapKey = "JAVA_OPTS", mapValue = "-server -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+PerfDisableSharedMem -XX:+ParallelRefProcEnabled -Xmx2g -Xms2g -XX:MaxPermSize=1024m" } ]
                    , secretEnvVars = ./secretEnvVars
                    }
                ]
            }

in   /usr/local/var/dhall-kubernetes/api/Deployment/mkDeployment config
