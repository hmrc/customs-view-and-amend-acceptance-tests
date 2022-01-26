#!/usr/bin/env bash

sbt -Denvironment=dev -Dbrowser=remote-chrome 'testOnly uk.gov.hmrc.runner.RunE2E'