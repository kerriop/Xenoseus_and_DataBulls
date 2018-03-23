import MySQLdb
import time
import xlrd

conn = MySQLdb.connect(host="localhost",
                       user="root",
                       passwd="",
                       db="awara")
cursor = conn.cursor()

orders = []
shops = []
seasons = []

start_time = time.clock()
rb = xlrd.open_workbook(r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\2k15\2015_2.xlsx')  # тут пишем свой путь
sheet = rb.sheet_by_index(0)
id = 0
for rownum in range(sheet.nrows):
    # try: # вот он срабатывает
    row = sheet.row_values(rownum)
    id += 1
    count = row[6]
    total = row[7]
    model = row[3]
    # print('>' + str(row[4]) + '<')
    color = str(row[4]).split(', ')[0]
    size = str(row[4]).split(', ')[1]

    if row[0] in orders:
        order_id = orders.index(row[0])
    else:
        orders.append(row[0])
        order_id = orders.index(row[0])
    # date1 = str((row[1]).replace('.', '*').split('*').reverse())
    date1 = str(row[1]).split(' ')[0].replace('.', '*').split('*') # так реверс тут ломается надо костыли делать
    # print(date1, type(date1))
    date1 = str(date1[2] + '*' + date1[1] + '*' + date1[0] + '*')
    date = date1 + str(row[1]).split(' ')[1].replace(':', '*')

    if row[2] in shops:
        shop_id = shops.index(row[2])
    else:
        shops.append(row[2])
        shop_id = shops.index(row[2])

    if row[9] in seasons:
        season_id = seasons.index(row[9])
    else:
        seasons.append(row[9])
        season_id = seasons.index(row[9])

    # Заносим в таблицу operations
    print(id, order_id, date, shop_id, model, color, size, season_id, count, total)
    cursor.execute(
        """INSERT INTO operations (id, order, date, shop, model, color, size, season, count, total) VALUES('%s', '%s', """ % (id, order_id) +
        """STR_TO_DATE('{}', %Y*%m*%d*%H*%i*%s')""".format(date) + """, '%s, '%s', '%s', '%s', '%s', '%s', '%s')""" % (shop_id, model, color, size, season_id, count, total))
    conn.commit()

    # except IndexError:
    #     print('id <<<<<', id)

# Заносим в таблицу orders
i = 0
for e in orders:
    cursor.execute(
        """INSERT INTO orders (id, number) VALUES('%s', '%s')""" % (
            i, e))
    conn.commit()
    i += 1

# Заносим в таблицу shops
i = 0
for e in shops:
    cursor.execute(
        """INSERT INTO shops (id, name) VALUES('%s', '%s')""" % (
            i, e))
    conn.commit()
    i += 1

# Заносим в таблицу seasons
i = 0
for e in orders:
    cursor.execute(
        """INSERT INTO seasons (id, name) VALUES('%s', '%s')""" % (
            i, e))
    conn.commit()
    i += 1

# ВНИМАНИЕ для работы необходимо руками удалить первые три строки из таблицы АХТУНГ

if __name__ == '__main__':
    # Для записи в БД необходимо добавить запись в БД в цикл, переменнык к в принтах ниже < Сделано
    print('id:', id)
    print('order:', order_id, '>>', orders[order_id])
    print('date:', date)
    print('shop:', shop_id, '>>', shops[shop_id])
    print('model:', model)
    print('color:', color)
    print('size:', size)
    print('season:', season_id, '>>', seasons[season_id])
    print('count:', count)
    print('total:', total)

    print(time.clock() - start_time, "seconds")  # время выполнения
    print('\n\n')

    # Вспомогательные БД
    print(orders)
    print(shops)
    print(seasons)
