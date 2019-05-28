package com.tangzhentao.networkutils;


import com.tangzhentao.network.repository.BsRepository;
import com.tangzhentao.network.utils.NetWorkUtils;


/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/22
 */
public class RiliRepository extends BsRepository {
    private BsViewModel bsViewModel = new BsViewModel();

    public RiliRepository() {
        bsViewModel = new BsViewModel();
    }

    public BsViewModel getBsViewModel() {
        return bsViewModel;
    }

    /**
     * 获取数据
     */
    public void  getCitys() {
        TestRequest request = new TestRequest();
        request.key = "40484e3f46f9a904b7cac4ea47cc5829";
        request.post(NetWorkUtils.HTTP_MODULE_CITYS ,bsViewModel.getOberverRiver());
    }
}
