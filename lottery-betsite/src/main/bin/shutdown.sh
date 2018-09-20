#!/bin/bash
kill `ps axu|grep lottery-betsite|grep -v grep |awk '{print $2}'`