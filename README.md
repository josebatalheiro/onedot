# Onedot Data Integration
This repo contains code for an example of a local spark pipeline. More detail of the implementation can be found in the 'Data Integration.docx' document.

## How to run
Run 'mvn clean install' on the project to generate a portable jar, and then just run it locally as such:
```bash
java -cp src/main/resources;target/data_task.jar org.onedot.IntegrateData
```
Otherwise, you can just run the IntegrateData class from your preferred IDE.
