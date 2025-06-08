import React, { useState } from 'react';
import { Button, Input, List, Modal, Typography, InputNumber, message } from 'antd';

const mockProducts = [
  { id: '1001', name: '黑森林蛋糕', price: 20 },
  { id: '1002', name: '卡布奇诺', price: 32 },
  { id: '1003', name: '草莓布丁', price: 18 },
  { id: '1004', name: '芒果双拼', price: 25 },
  { id: '1005', name: '牛角包', price: 18 },
];

const Checkout: React.FC = () => {
  const [barcode, setBarcode] = useState('');
  const [cart, setCart] = useState<any[]>([]);
  const [successModal, setSuccessModal] = useState(false);

  const handleAdd = () => {
    const product = mockProducts.find((p) => p.id === barcode.trim());
    if (!product) {
      message.error('未找到该商品');
      return;
    }
    setCart((prev) => {
      const exist = prev.find((item) => item.id === product.id);
      if (exist) {
        return prev.map((item) =>
          item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prev, { ...product, quantity: 1 }];
    });
    setBarcode('');
  };

  const handleQuantityChange = (id: string, value: number) => {
    setCart((prev) =>
      prev.map((item) => (item.id === id ? { ...item, quantity: value } : item))
    );
  };

  const handleCheckout = () => {
    setSuccessModal(true);
    setCart([]);
  };

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  return (
    <div style={{ padding: 32, background: '#fff', minHeight: '100vh' }}>
      <Typography.Title level={2}>结账界面</Typography.Title>
      <div style={{ margin: '24px 0', fontSize: 20 }}>
        <span>（输入框）输入商品条形码（id），输入完成自动添加到下方的商品列表中</span>
      </div>
      <div style={{ display: 'flex', gap: 8, marginBottom: 24 }}>
        <Input
          placeholder="输入条形码（id）"
          value={barcode}
          onChange={(e) => setBarcode(e.target.value)}
          onPressEnter={handleAdd}
          style={{ width: 240, fontSize: 18 }}
        />
        <Button type="primary" onClick={handleAdd} style={{ fontSize: 18 }}>
          添加
        </Button>
      </div>
      <List
        bordered
        dataSource={cart}
        style={{ maxWidth: 600, background: '#fafafa' }}
        renderItem={(item) => (
          <List.Item style={{ fontSize: 20, display: 'flex', alignItems: 'center' }}>
            <span style={{ flex: 1 }}>{item.name}</span>
            <InputNumber
              min={1}
              value={item.quantity}
              onChange={(value) => handleQuantityChange(item.id, value || 1)}
              style={{ margin: '0 16px', width: 60 }}
            />
            <span style={{ width: 80, textAlign: 'right' }}>￥{item.price * item.quantity}</span>
          </List.Item>
        )}
        locale={{ emptyText: '暂无商品' }}
      />
      <div style={{ marginTop: 24, fontSize: 22, textAlign: 'right', maxWidth: 600 }}>
        总计：<span style={{ color: 'red' }}>￥{total}</span>
      </div>
      <div style={{ textAlign: 'right', marginTop: 32, maxWidth: 600 }}>
        <Button type="primary" size="large" onClick={handleCheckout}>
          结算
        </Button>
      </div>
      <Modal
        open={successModal}
        onCancel={() => setSuccessModal(false)}
        onOk={() => setSuccessModal(false)}
        okText="确定"
        cancelButtonProps={{ style: { display: 'none' } }}
      >
        <Typography.Title level={3} style={{ textAlign: 'center' }}>
          结账成功
        </Typography.Title>
      </Modal>
    </div>
  );
};

export default Checkout; 