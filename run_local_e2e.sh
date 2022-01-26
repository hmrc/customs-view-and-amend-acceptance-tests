#!/usr/bin/env bash

sbt -Denvironment=local -Dbrowser=chrome 'testOnly uk.gov.hmrc.runner.RunE2E'
