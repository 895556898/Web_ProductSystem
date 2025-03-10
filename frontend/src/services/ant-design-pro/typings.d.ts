// @ts-ignore
/* eslint-disable */

declare namespace API {
  type CurrentUser = {
    name?: string;
    avatar?: string;
    userid?: string;
    email?: string;
    signature?: string;
    title?: string;
    group?: string;
    tags?: { key?: string; label?: string }[];
    notifyCount?: number;
    unreadCount?: number;
    country?: string;
    access?: string;
    geographic?: {
      province?: { label?: string; key?: string };
      city?: { label?: string; key?: string };
    };
    address?: string;
    phone?: string;
  };

  type LoginResult = {
    sessionID?: string,
    status?: string;
    type?: string;
    currentAuthority?: string;
  };

  type RegisterResult = {
    sessionID?: string,
    status?: string;
  }

  type PageParams = {
    current?: number;
    pageSize?: number;
  };

  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };

  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type FakeCaptcha = {
    code?: number;
    status?: string;
  };

  type RegisterParams = {
    username?: string;
    password?: string; //实际上是sha256的hashed_password
    email?: string;
    autoLogin?: boolean;
  }

  type LoginParams = {
    username?: string; //邮箱也可以
    password?: string;
    autoLogin?: boolean;
  };

  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };

  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
  type ProductPageParam = PageParams & {
    name?: string;
    origin?: string;
  }

  type ProductList = {
    records?: ProductListItem[];
    pageNumber?: number;
    pageSize?: number;
    totalPage?: number;
    totalRow?: number;
  }

  type ProductListItem = {
    id?: number;
    name?: string;
    price?: number;
    cost?: number;
    origin?: string;
    category?: string;
    stock?: number;
    brand?: string;
    productionDate?: string;
    shelfLife?: string;
    createdAt?: string;
  };

  type APIStatusObject = {
    code: number;
    msg: string;
    status: string;
  }

  type ProductLogList = {
    records?: ProductLogItem[];
    pageNumber?: number;
    pageSize?: number;
    totalPage?: number;
    totalRow?: number;
  }

  type ProductLogItem = {
    id?: number;
    itemId?: number;
    name?: string;
    action?: string;
    createdAt?: Date;
    createdBy?: string;
  }
}
