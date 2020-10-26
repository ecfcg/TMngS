# install
https://wiki.postgresql.org/wiki/Apt

## スクリプトからインストール
```
$ sudo apt-get install curl ca-certificates gnupg
$ curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
$ sudo apt-get update
$ sudo apt-get install postgresql-11 pgadmin4
```

### 確認
```
$ psql --version
psql (PostgreSQL) 13.0 (Ubuntu 13.0-1.pgdg20.04+1)
```

## postgresユーザ(Ubuntu)のパスワードを変更
```
$ sudo passwd postgres
postgres
```

## postgresロール(postgres)のパスワードを変更
```
$ sudo login postgres
$ psql
# alter role postgres with login encrypted password 'postgres';
# exit
$ exit
```

## パスワード認証に変更
```
$ sudo cp /etc/postgresql/13/main/pg_hba.conf /etc/postgresql/13/main/pg_hba.conf.org
```

## DB作成
### ユーザを作成する
```
$ sudo -u postgres psql
# create role tmngs with createdb login encrypted password 'tmngs';
# exit

```

### DBを作成する
```
$ createdb -U tmngs tmngs
```

## ddl実行
```
$ psql -f ./create_table/m_holiday.sql -U tmngs -d tmngs
```

