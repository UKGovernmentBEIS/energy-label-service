import SwaggerParser from "@apidevtools/swagger-parser";
import fetch from 'node-fetch';
import {createWriteStream} from 'node:fs';
import {pipeline} from 'node:stream';
import {promisify} from 'node:util'
import pdf2img from "pdf-img-convert"
import fs from "fs";
import {PNG} from "pngjs";

// const baseUrl = "https://energy-label-service-qa.london.cloudapps.digital";
// const folderName = "old";

// const baseUrl = "https://dev.energylabels.energysecurity.gov.uk";
// const folderName = "new";

const baseUrl = "http://localhost:8080";
const folderName = "new";

function wait(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

SwaggerParser.dereference(baseUrl+"/api/v1/energy-labels-openapi.json").then(async api => {

  for (const [path, obj] of Object.entries(api.paths)) {

    await wait(500);

    const url = baseUrl + path;
    const requestBodyObj = {};
    for (const [name, p] of Object.entries(obj.post.requestBody.content['application/json'].schema.properties)) {
      requestBodyObj[name] = p.example;
    }

    // Energy labels and fiches
    if (requestBodyObj.outputFormat !== undefined) {
      // generate both PNG and PDF versions.
      requestBodyObj.outputFormat = 'PNG';
      const pngOutputPath = `./${folderName}/${path.replaceAll('/', '_')}-PNG.png`;
      await doRequest(url, requestBodyObj, pngOutputPath);
      const nativePng = PNG.sync.read(fs.readFileSync(pngOutputPath));
      const pngWidth = nativePng.width;
      const pngHeight = nativePng.height;
      console.log(`${path} PNG OK`);

      await wait(200);
      requestBodyObj.outputFormat = 'PDF';
      const pdfOutputPath = `./${folderName}/${path.replaceAll('/', '_')}-PDF.pdf`
      await doRequest(url, requestBodyObj, pdfOutputPath);

      // convert PDF to PNG, so we can check the PDFs have also generated ok
      const imageOut = await pdf2img.convert(pdfOutputPath, {width: pngWidth, height: pngHeight});
      for (let i = 0; i < imageOut.length; i++) {
        fs.writeFileSync(pdfOutputPath.replace(".pdf", ".png"), imageOut[i]);
      }

      console.log(`${path} PDF OK`);
    }

    await wait(200);
    // Internet arrows
    if (requestBodyObj.labelFormat !== undefined) {
      requestBodyObj.labelFormat = 'PNG';
      await doRequest(url, requestBodyObj, `./${folderName}/${path.replaceAll('/', '_')}.png`);
      console.log(`${path} PNG OK`);
    }

  }
})

async function doRequest(url, requestBody, outputFilePath) {
  const response = await fetch(url, {
    method: 'post',
    body: JSON.stringify(requestBody),
    headers: {'Content-Type': 'application/json'}
  });
  const streamPipeline = await promisify(pipeline);
  await streamPipeline(response.body, createWriteStream(outputFilePath));
}



