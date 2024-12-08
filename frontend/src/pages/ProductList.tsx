import {
  ActionType, ModalForm,
  ProColumns,
  ProForm,
  ProFormDatePicker,
  ProFormText,
  ProTable
} from '@ant-design/pro-components';
import React, { useRef, useState } from 'react';
import { addProduct, deleteProduct, getProductList, updateProduct } from '@/services/ant-design-pro/api';
import { DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, message, Modal } from 'antd';
const ProductList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [mode, setMode] = useState<'create' | 'edit'>('create');
  const [modalVisit, setModalVisit] = useState<boolean>(false);
  const [recordValue, setRecordValue] = useState<API.ProductListItem | undefined>(undefined);
  //如果想复用的话可以把columns提取到别的地方
  const columns : ProColumns<API.ProductListItem>[] = [
    {
      title: '商品名',
      dataIndex: 'name',
      copyable: true,
      ellipsis: true,
      formItemProps: {
        rules: [
          {
            required: true,
            message: '此项为必填项',
          },
        ],
      },
    },
    {
      title: '价格',
      dataIndex: 'price',
      valueType: 'money',
      hideInSearch: true,
    },
    {
      title: '成本',
      dataIndex: 'cost',
      valueType: 'money',
      hideInSearch: true,
    },
    {
      title: '库存',
      dataIndex: 'stock',
      hideInSearch: true,
    },
    {
      title: '产地',
      dataIndex: 'origin',
    },
    {
      title: '生产日期',
      dataIndex: 'productionDate',
      sorter: true,
      hideInSearch: true,
    },
    {
      title: '创建时间',
      key: 'showTime',
      dataIndex: 'createdAt',
      valueType: 'dateTime',
      sorter: true,
      hideInSearch: true,
    },
    {
      title: '操作',
      valueType: 'option',
      key: 'option',
      render: (text, record, _, action) => [
        <>
        <Button icon={<DeleteOutlined />}
                type="primary"
                danger={true}
                onClick={async () => {
                  Modal.confirm({
                    title: '确认删除',
                    content: '确定要删除此产品吗？',
                    okText: '删除',
                    okType: 'danger',
                    cancelText: '取消',
                    onOk: async () => {
                      if (typeof record.id === 'number') {
                        const res = await deleteProduct(record.id);
                        if(res.status === "ok"){
                          message.success('删除成功');
                          actionRef.current?.reload();
                          return true;
                        }else{
                          message.error('删除失败');
                          return false;
                        }
                      }
                    },
                  });
                }}>
          删除
        </Button>
        <Button icon={<EditOutlined />}
                type="primary"
                onClick={async () => {
                  setRecordValue(record);
                  setMode('edit');
                  setModalVisit(true);
                }}>
          编辑
        </Button>
        </>
      ]
    }
  ];
  return (
    <>
      <ModalForm
        title={mode === 'create' ? '新建产品' : '编辑产品'}
        open={modalVisit}
        modalProps={{ destroyOnClose: true }}
        initialValues={recordValue}
        onFinish={
          async (values) => {
            let msg;
            if (mode === 'create') {
              msg = await addProduct(values);
            } else {
              msg = await updateProduct({
                id: recordValue?.id,
                ...values
              });
            }

            if (msg.status === 'ok') {
              message.success(mode === 'create' ? '添加成功' : '修改成功');
              actionRef.current?.reload();
              setModalVisit(false);
              return true;
            } else {
              message.error(mode === 'create' ? '添加失败:' : '修改失败:' + msg.msg);
              return false;
            }
          }
        }
        onOpenChange={setModalVisit}
      >
        <ProForm.Group>
          <ProFormText
            width="md"
            name="name"
            label="商品名称"
            placeholder="请输入名称"
            required={true}
          />
          <ProFormText
            width="md"
            name="price"
            label="价格"
            placeholder="请输入价格"
            required={true}
          />
          <ProFormText
            width="md"
            name="cost"
            label="成本"
            placeholder="请输入成本"
            required={true}
          />
          <ProFormText
            width="md"
            name="stock"
            label="库存"
            placeholder="请输入库存"
            required={true}
          />
          <ProFormText
            width="md"
            name="category"
            label="类别"
            placeholder="请输入商品类别"
            required={true}
          />
          <ProFormText
            width="md"
            name="origin"
            label="产地"
            placeholder="请输入产地"
            required={true}
          />
          <ProFormText
            width="md"
            name="brand"
            label="品牌"
            placeholder="请输入品牌"
            required={true}
          />
          <ProFormDatePicker
            name="productionDate"
            label={'生产日期'}
            required={true}
            dataFormat={'YYYY-MM-DD'}
          />
        </ProForm.Group>
      </ModalForm>

    <ProTable<API.ProductListItem, API.PageParams>
    columns={columns}
    rowKey="id"
    actionRef={actionRef}
    toolBarRender={() => [
      <Button
        key="button"
        icon={<PlusOutlined />}
        onClick={() => {
          setRecordValue(undefined);
          setModalVisit(true);
        }}
        type="primary"
      >
        新建
      </Button>,
      ]}
    // params 是需要自带的参数
    // 这个参数优先级更高，会覆盖查询表单的参数
      request={async (
      // 第一个参数 params 查询表单和 params 参数的结合
      // 第一个参数中一定会有 pageSize 和  current ，这两个参数是 antd 的规范
      params: API.ProductPageParam,
      sort,
      filter,
    ) => {
      // 这里需要返回一个 Promise,在返回之前你可以进行数据转化
      // 如果需要转化参数可以在这里进行修改
      const msg = await getProductList(params);
      return {
        data: msg.records,
        // success 请返回 true，
        // 不然 table 会停止解析数据，即使有数据
        success: true,
        // 不传会使用 data 的长度，如果是分页一定要传
        total: msg.totalRow,
      };
    }}
  />
  </>
  )
}


export default ProductList;
