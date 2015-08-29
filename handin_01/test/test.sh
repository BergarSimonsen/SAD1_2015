#!/bin/bash
echo "starting test!";
java SMTestTool sm-illiad-out.txt 01.txt;
java SMTestTool sm-kt-p-4-out.txt 02.txt;
java SMTestTool sm-kt-p-5-out.txt 03.txt;
java SMTestTool sm-random-500-out.txt 04.txt;
java SMTestTool sm-random-50-out.txt 05.txt;
java SMTestTool sm-random-5-out.txt 06.txt;
java SMTestTool sm-worst-500-out.txt 07.txt;
java SMTestTool sm-worst-50-out.txt 08.txt;
java SMTestTool sm-worst-5-out.txt 09.txt;
java SMTestTool sm-bbt-out.txt 10.txt;
java SMTestTool sm-friends-out.txt 11.txt;
echo "finished test!";
