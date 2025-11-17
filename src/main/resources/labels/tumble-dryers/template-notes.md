For the condenser tumble dryers template, you can add the classes condensationEfficiencyClassA through to condensationEfficiencyClassG to the condensationEfficiency element to make the applicable condensation efficiency rating larger and bold. This should be in addition to the cls-70 class already on the element.

For the 2025 label: 
* Add the classes `subscaleA` through to `subscaleE` to the `repairabilityClass`, `condensingClass` and `soundClass` elements to make the applicable subscale class rating larger and bold. This should be in addition to the classes already on the element.
* There are four variants of this label, which show different icons in the top row:
  * No repairability, non-condensing
    * Remove the `repairabilityIcon` and `condensingIcon` elements
    * Add `transform="translate(-75.5)"` to the `soundIcon` element to position the sound icon in the centre of the label
  * No repairability, condensing
    * Remove the `repairabilityIcon` element
    * Add `transform="translate(-42)"` to `condensingIcon` to position the condensing icon above the capacity icon  
    * Add `transform="translate(-24)"` to `soundIcon` to position the sound icon above the duration icon
  * Repairability, non-condensing
    * Remove the `condensingIcon` element
    * Add `transform="translate(43)"` to `repairabilityIcon` to position the repairability icon above the capacity icon
    * Add `transform="translate(-24)"` to `soundIcon` to position the sound icon above the duration icon
  * Repairability, condensing
    * No adjustments required - this variant show all three icons in the top row