The label templates show the letter at 100px high, so to calculate the scale factor for the template to ensure the letter is the target text size in pixels (t), just do t/100.

The actual rendered height of the letter may vary by a few pixels, depending on the letter. This is because different letters have slightly different heights - for example, G is taller than A.

The colour of the arrow should be the same as the selected rating on the full label for the specific product category. Apply a class from rating1 to rating7 to the ratingArrow element to get the correct background colour. If the selected product category has over 7 rating levels, apply rating7 (red) to any more beyond the seventh.