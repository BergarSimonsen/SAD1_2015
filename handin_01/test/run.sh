#!/bin/bash
echo "starting!";
java StableMatching < sm-illiad-in.txt > 01.txt;
java StableMatching < sm-kt-p-4-in.txt > 02.txt;
java StableMatching < sm-kt-p-5-in.txt > 03.txt;
java StableMatching < sm-random-500-in.txt > 04.txt;
java StableMatching < sm-random-50-in.txt > 05.txt;
java StableMatching < sm-random-5-in.txt > 06.txt;
java StableMatching < sm-worst-500-in.txt > 07.txt;
java StableMatching < sm-worst-50-in.txt > 08.txt;
java StableMatching < sm-worst-5-in.txt > 09.txt;
java StableMatching < sm-bbt-in.txt > 10.txt
java StableMatching < sm-friends-in.txt > 11.txt
echo "done!";
