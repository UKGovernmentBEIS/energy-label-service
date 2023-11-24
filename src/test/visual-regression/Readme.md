# Visual regression test script

Quick script to generate all energy labels, internet arrows and fiches, form 2 different deployments via the API. Then diff the images see changes between the deployments to catch any regression issues. 

## Process
The `fetch-all-images.js` script will:
- Fetch and parse the OpenAPI file from the specified environment
- Loop over each endpoint and generate the PDF and PNG versions of each label, and save to disk
- Convert PDFs to PNG. We can only diff PNGs, so in order to check the PDF output hasn't changed either we need to then convert the PDFs to PNG.

Once this has been run twice to create an old and new set of images, the `compare-images.js` script will:
- Load both versions of each PNG and find pixels that are different between the two
- For those with an amount of differing pixels over the given threshold, a composite diff images is created. Showing the diff, the old image and the new image, highlighting the areas that are different.

## Instructions

### 1. Collect baseline images
1. Create a directory to store the baseline images.
2. In `fetch-all-images.js`, set the `baseUrl` to point to an existing baseline deployment (e.g. the staging env) and set `folderName` to the folder you just created. 
3. Run `fetch-all-iamges.js`. Your folder should now have a load of images.

### 2. Collect new images
1. Create a directory to store the new images.
2. In `fetch-all-images.js`, set the `baseUrl` to point to your new deployment (e.g. localhost) and set `folderName` to the folder you just created.
3. Run `fetch-all-iamges.js`. Your folder should now have a load of images. 

### 4. Run diff
1. Create a directory to store the visual diff output.
2. In `compare-images.js` set the `oldDir`, `newDir` and `diffDir` fields to the 3 directories you created.
3. Run `compare-images.js`.
4. Review visual diff output for changes and action as needed.

