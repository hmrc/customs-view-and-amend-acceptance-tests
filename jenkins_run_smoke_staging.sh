#!/usr/bin/env bash

sbt -Denvironment=staging -Dbrowser=remote-chrome 'testOnly uk.gov.hmrc.runner.RunE2E'