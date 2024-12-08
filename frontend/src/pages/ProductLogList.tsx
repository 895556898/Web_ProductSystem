import { ProColumns, ProTable } from '@ant-design/pro-components';
import { getProductLogList } from '@/services/ant-design-pro/api';
import React from 'react';

const ProductLogList: React.FC = () => {
  const columns: ProColumns<API.ProductLogItem>[] = [
    {
      title: '记录ID',
      dataIndex: 'id',
      hideInSearch: true
    },
    {
      title: '商品ID',
      dataIndex: 'itemId',
      hideInSearch: true
    },
    {
      title: '商品名',
      dataIndex: 'name',
      copyable: true,
      ellipsis: true,
      hideInSearch: true
    },
    {
      title: '操作行为',
      dataIndex: 'action',
      hideInSearch: true
    },
    {
      title: '记录时间',
      dataIndex: 'createdAt',
      valueType: 'dateTime',
      hideInSearch: true
    },
    {
      title: '操作人',
      dataIndex: 'createdBy',
      hideInSearch: true
    }
  ];

  return (
    <>
      <ProTable<API.ProductLogItem, API.PageParams>
        columns={columns}
        rowKey="id"
        // params 是需要自带的参数
        // 这个参数优先级更高，会覆盖查询表单的参数
        request={async (
          // 第一个参数 params 查询表单和 params 参数的结合
          // 第一个参数中一定会有 pageSize 和  current ，这两个参数是 antd 的规范
          params: API.PageParams,
          sort,
          filter
        ) => {
          // 这里需要返回一个 Promise,在返回之前你可以进行数据转化
          // 如果需要转化参数可以在这里进行修改
          const msg = await getProductLogList(params);

          const recordsWithDefaultName = (msg?.records || []).map(record => ({
            ...record,
            name: record.name || '已删除' // 设置默认商品名为 '默认商品名'
          }));
          return {
            data: recordsWithDefaultName,
            // success 请返回 true，
            // 不然 table 会停止解析数据，即使有数据
            success: true,
            // 不传会使用 data 的长度，如果是分页一定要传
            total: msg.totalRow
          };
        }}
      />
    </>
  );
};

export default ProductLogList;
