#!/bin/bash -e

echo "****************************************************************************************************"
echo "********************************* Starting all dependency services *********************************"
echo "Starting CDSRC_ALL"
sm --start CDSRC_ALL --wait 30 --noprogress
