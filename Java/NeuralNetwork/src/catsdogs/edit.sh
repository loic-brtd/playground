#!/bin/bash

files=$(ls train)
total=$(ls -1 train | wc -l)

echo "Total: $total files"

index=0
for file in $files; do
  index=$((index+1))
  percentage=$((index*100/total))
  echo "Resizing $index/$total... ($percentage%)";
  convert -resize 28x28! "train/$file" "resized/$file" 
done
