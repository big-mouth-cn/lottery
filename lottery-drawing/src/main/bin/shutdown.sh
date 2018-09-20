#!/bin/bash
kill `ps axu|grep lottery-drawing|grep -v grep |awk '{print $2}'`