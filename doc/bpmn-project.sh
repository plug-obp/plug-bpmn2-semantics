#!/bin/bash


#create a directory to hold the project
mkdir bpmn-project

#go to that directory
cd bpmn-project

#clone  plug-devel from bitbucket
git clone git@bitbucket.org:plug-team/plug-devel.git

#clone the BPMN2 examples from github
git clone https://github.com/plug-obp/plug-bpmn2-examples.git

#clone the semantics from github
git clone https://github.com/plug-obp/plug-bpmn2-semantics.git

#go to plug-devel
cd plug-devel

#build the project
./gradlew build

#generate intellij project
./gradlew idea

#run the example
./gradlew :plug-bpmn2-semantics:run