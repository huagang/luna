#! /bin/bash

jps -lm | grep LunaProvider | cut -d" " -f1 | xargs kill
