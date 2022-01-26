#! /bin/sh

sbt -Dbrowser=chrome -Denvironment=local  'testOnly uk.gov.hmrc.runner.RunZAP'
