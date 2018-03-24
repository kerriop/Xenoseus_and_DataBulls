from MySQLdb import *
import xlrd
from pandas.io import sql
from sqlalchemy import create_engine
import pandas as pd
import numpy as np


PATH = './BonchHackDay\'18/'

seasons = [s + y for s in ['AW', 'SS'] for y in ['15', '16', '17', '18']]
data = []


# def read_file(file):
#     global data
#     data = pd.read_excel(file, header=2)
#     data.drop('НДС', axis=1)
#     data['Сезон'] = data['Сезон'].apply(lambda s: s if s in seasons else 'OTHER')
#     return data
#
#
# def get_numbers():
#     # print(len(data['Номер']))
#     with engine.connect() as conn, conn.begin():
#         return data['Номер'].to_sql('order', conn, if_exists='replace')


if __name__ == '__main__':

    file1 = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\2k15\2015.xlsx'
    file2 = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\2016\2016.xlsx'
    file3 = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\2017\2017.xlsx'
    # file4 = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\2018\2018.xlsx'

    master_file1AW = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW15_MASTER.xls'
    master_file1SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS15_MASTER.xlsx'

    master_file2AW = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW16_MASTER.xlsx'
    master_file2SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS16_MASTER.xlsx'

    master_file3AW = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW17_MASTER.xlsx'
    master_file3SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS17_MASTER.xlsx'


    master_file4AW = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW13_MASTER.xlsx'
    master_file4SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS13_MASTER.xls'

    master_file5AW = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW14_MASTER.xlsx'
    master_file5SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS14_MASTER.xlsx'

    # master_file6SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\AW18_MASTER.xlsx'
    # master_file6SS = r'C:\Users\Dima\YandexDisk\HACKATON_2018\python\other_files\data\master\SS18_MASTER.xlsx'

    engine = create_engine('mysql://root:@localhost/awara?charset=utf8&use_unicode=1')

    # master_dataSS = pd.read_excel(master_file1SS, header=0)
    # master_dataAW = pd.read_excel(master_file1AW, header=0)
    data = pd.DataFrame()
    master_data = pd.DataFrame()

    # for file in [file1, file2, file3]:
       # data = data.append(pd.read_excel(file, header=2))
    # data.drop('НДС', axis=1)
    # data['Сезон'] = data['Сезон'].apply(lambda s: s if s in seasons else 'OTHER')

    for file in [master_file1AW, master_file1SS, master_file2AW, master_file2SS, master_file3AW, master_file3SS, master_file4AW, master_file4SS, master_file5AW, master_file5SS]:
        master_data = master_data.append(pd.read_excel(file, header=0))

    with engine.connect() as conn, conn.begin():
        #orders
        # pd.DataFrame(data['Номер'].unique()).to_sql('orders', conn, if_exists='replace')
        #seasons
        # pd.DataFrame(data['Сезон'].unique()).to_sql('seasons', conn, if_exists='replace')
        #shops
        # pd.DataFrame(data['Магазин'].unique()).to_sql('shops', conn, if_exists='replace')

        #operations
        # data.drop('НДС', axis=1).to_sql('operations', conn, if_exists='replace')


        #products
        master_data[['Сезон','Модель','Код цвета','Размер','Штрих-код']].to_sql('products',conn,if_exists='replace')

