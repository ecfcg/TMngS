#!/bin/sh

# スクリプトの絶対パスを取得
script_dir=$(cd $(dirname $0); pwd)

# create tableをすべて実行
for table_script in `ls $script_dir/table`; do
  psql -f $script_dir/table/$table_script -h 127.0.0.1 -U tmngs_test -d tmngs
done

