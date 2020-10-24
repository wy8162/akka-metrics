#!/usr/bin/env bash

profile=$1
[[ -z $profile ]] && profile=quickstart

mvn exec:exec -P ${profile}