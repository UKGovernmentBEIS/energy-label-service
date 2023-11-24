import pixelmatch from "pixelmatch"
import fs from "fs"
import {PNG} from "pngjs";
import combineImage from 'combine-image';

const oldDir = "old";
const newDir = "new";
const diffDir = "diff";

for (const imgFile of fs.readdirSync(`./${oldDir}`)) {
  if (imgFile.endsWith(".pdf")) continue; // ignore pdf, we use the converted image

  const imgOld = PNG.sync.read(fs.readFileSync(`./${oldDir}/${imgFile}`));
  const imgNew = PNG.sync.read(fs.readFileSync(`./${newDir}/${imgFile}`));

  const {width, height} = imgOld; // new image should have same w/h

  const diff = new PNG({width, height});

  const pxDiffCount = pixelmatch(imgOld.data, imgNew.data, diff.data, width, height, {threshold: 0.1});

  const logMsg = `px diff count: ${pxDiffCount} for ${imgFile}`;
  if (pxDiffCount > 10) {
    fs.writeFileSync(`./${diffDir}/${imgFile}`, PNG.sync.write(diff));

    const combined = await combineImage([`./${diffDir}/${imgFile}`, `./${oldDir}/${imgFile}`, `./${newDir}/${imgFile}`])
    await combined.write(`./${diffDir}/combined-${imgFile}`);

    console.warn(logMsg);
  } else {
    console.log(logMsg);
  }

}