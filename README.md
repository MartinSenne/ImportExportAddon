# Import / Export  for Vaadin 7

## Setup Maven multi-module project

The project is setup as a multi-module Maven project:

* import-export-root
  * import-export-demo (depends on import-export-addon)
  * import-export-addon 


## Compile and run

* Invoke Maven target `clean install` on *import-export-addon*.
* Invoke Maven target `clean install` on *import-export-demo*.
* Invoke Maven target `jetty:run` on *import-export-demo* to startup the Jetty server.

Application will be available under `localhost:8080`.
