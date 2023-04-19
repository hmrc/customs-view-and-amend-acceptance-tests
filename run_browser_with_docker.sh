#!/bin/bash
#######################################
# The script starts a remote-chrome, remote-firefox or remote-edge docker container for running Browser tests on a developer machine only.
# The container directs TCP requests from the container to the host machine enabling testing services running via Service Manager.
# WARNING: Do not use this script in the Jenkins Continuous Integration environment
#
# Arguments:
#   remote-chrome, remote-firefox or remote-edge
#
# Output:
#   Starts  chrome, firefox or edge docker containers from chrome-with-rinetd, firefox-with-rinetd or edge-with-rinetd image respectively
#######################################
# Initialises port_mappings with the port numbers of all running application using the lsof command.
port_mappings=$(sm2 --status | grep PASS | awk '{ print $8"->"$8 }' | paste -sd "," -)
ipConfigAddress=$(ifconfig en0 | grep 'inet ' | awk '{print $2}')
# Alternatively, port_mappings can be explicitly initialised as below:
port_mappings=$"12346->12346,9022->9022,9028->9028,9032->9032,8500->8500,9612->9612,8585->8585,9949->9949,9871->9871,9557->9557,7501->7501,7502->7502,9250->9250,9893->9893,9898->9898,8989->8989,9744->9744,9753->9753,9399->9399,8300->8300,9514->9514,8950->8950,9927->9927,8400->8400,8086->8086,9995->9995,12345->12345,10110->10110,9571->9571,9570->9570,9978->9978"
# This list of port mappings should be checked against the service manager profile
# CDSRC_ALL
#######################################
# Defines the BROWSER variable from the argument passed to the script
#######################################
if [ -z "${1}" ]; then
    echo "ERROR: Browser type not specified. Re-run the script with the option remote-chrome, remote-firefox or remote-edge."
    exit 1
elif [ "${1}" = "remote-chrome" ]; then
    BROWSER="artefacts.tax.service.gov.uk/chrome-with-rinetd:latest"
elif [ "${1}" = "remote-firefox" ]; then
    BROWSER="artefacts.tax.service.gov.uk/firefox-with-rinetd:latest"
elif [ "${1}" = "remote-edge" ]; then
    BROWSER="artefacts.tax.service.gov.uk/edge-with-rinetd:latest"
fi

#######################################
# Terminates other browser containers and starts a new one
#######################################
docker stop $(docker ps --filter name=${1} --format="{{.ID}}")
docker pull ${BROWSER} \
    && docker run \
        -d \
        --rm \
        --name "${1}" \
        --shm-size=2g \
        -p 4444:4444 \
        -p 5900:5900 \
        -e PORT_MAPPINGS="$port_mappings" \
        -e TARGET_IP="$ipConfigAddress" \
        ${BROWSER}