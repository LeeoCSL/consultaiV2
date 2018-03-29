package br.com.consultai.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.githang.stepview.StepView;

import java.util.Arrays;
import java.util.List;

import br.com.consultai.R;
import br.com.consultai.adapter.ViewPagerAdapter;
import br.com.consultai.fragments.fragmentsCompra.BandeiraFragment;
import br.com.consultai.fragments.fragmentsCompra.SelecBilheteFragment;
import br.com.consultai.fragments.fragmentsCompra.CadastrarBilheteFragment;
import br.com.consultai.fragments.fragmentsCompra.CadastrarCreditoFragment;
import br.com.consultai.fragments.fragmentsCompra.ComprarFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidoBoletoFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidoFinalizadoCredFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidosFragment;
import br.com.consultai.fragments.fragmentsCompra.SelecBancoFragment;
import br.com.consultai.fragments.fragmentsCompra.SelecCreditoFragment;
import br.com.consultai.fragments.fragmentsCompra.TipoCompraFragment;
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFinalizadaFragment;
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFragment;
import br.com.consultai.model.Pagamento;
import br.com.consultai.util.CustomViewPager;
import br.com.consultai.util.ZoomOutPageTransformer;

public class ComprarActivity extends AppCompatActivity {
   public static ImageView circulo1, circulo2, circulo3, traco1, traco2;

    public static CustomViewPager mViewPager;
    public static int selecaoBanco = 0;
//    Button testebtn;

    int i = 1;
    public static Pagamento pagamento = new Pagamento();

    private SelecBilheteFragment mSelecBilheteFragment;
    private CadastrarBilheteFragment mCadastrarBilheteFragment;
    private ComprarFragment mComprarFragment;
    private SelecCreditoFragment mSelecCreditoFragment;
    private CadastrarCreditoFragment mCadastrarCreditoFragment;
    private PedidoFinalizadoCredFragment mPedidoFinalizadoCredFragment;
    private PedidoBoletoFragment mPedidoBoletoFragment;
    private SelecBancoFragment mSelecBancoFragment;
    private TransferenciaFragment mTransferenciaFragment;
    private TransferenciaFinalizadaFragment mTransferenciaFinalizadaFragment;
    private BandeiraFragment mBandeiraFragment;
    private TipoCompraFragment mTipoCompraFragment;

    private PedidosFragment mPedidosFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

//        testebtn = (Button) findViewById(R.id.testebtn);

        circulo1 = (ImageView) findViewById(R.id.circulo1);
        circulo2 = (ImageView) findViewById(R.id.circulo2);
        circulo3 = (ImageView) findViewById(R.id.circulo3);
        traco1 = (ImageView) findViewById(R.id.traco1);
        traco2 = (ImageView) findViewById(R.id.traco2);

        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);

        mViewPager.setCurrentItem(0, false);
        mViewPager.setPagingEnabled(false);
        atualizaStepView();
        setupViewPager(mViewPager);






    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //inicio
        mSelecBilheteFragment = new SelecBilheteFragment();
        mCadastrarBilheteFragment = new CadastrarBilheteFragment();
        //valor e tipo
        mComprarFragment = new ComprarFragment();
        //fluxo cred
        mSelecCreditoFragment = new SelecCreditoFragment();
        mCadastrarCreditoFragment = new CadastrarCreditoFragment();
        mPedidoFinalizadoCredFragment = new PedidoFinalizadoCredFragment();
        //fluxo boleto
        mPedidoBoletoFragment = new PedidoBoletoFragment();
        //fluxo transf
        mSelecBancoFragment = new SelecBancoFragment();
        mTransferenciaFragment = new TransferenciaFragment();
        mTransferenciaFinalizadaFragment = new TransferenciaFinalizadaFragment();


        mTipoCompraFragment = new TipoCompraFragment();


        mBandeiraFragment = new BandeiraFragment();


        adapter.addFragment(mSelecBilheteFragment);//0
        adapter.addFragment(mCadastrarBilheteFragment);//1
        adapter.addFragment(mComprarFragment); // 2
        adapter.addFragment(mSelecCreditoFragment); // 3
        adapter.addFragment(mCadastrarCreditoFragment); // 4
        adapter.addFragment(mPedidoFinalizadoCredFragment); // 5
        adapter.addFragment(mPedidoBoletoFragment); //6
        adapter.addFragment(mSelecBancoFragment); //7
        adapter.addFragment(mTransferenciaFragment); //8
        adapter.addFragment(mTransferenciaFinalizadaFragment); //9
        adapter.addFragment(mBandeiraFragment); // 10
        adapter.addFragment(mTipoCompraFragment); //11
        viewPager.setAdapter(adapter);
        viewPager.setAnimation(null);
    }

    public static void atualizaStepView(){
        if(mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 11){
            ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_selec_200);
            ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_cinza_200);
            ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_cinza_200);
            ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_cinza_200);
            ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_cinza_200);
        }

        else if(mViewPager.getCurrentItem() == 2 || mViewPager.getCurrentItem() == 3 || mViewPager.getCurrentItem() == 4 ||
                mViewPager.getCurrentItem() == 7 || mViewPager.getCurrentItem() == 8 || mViewPager.getCurrentItem() == 10){
            ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_checkbox_200);
            ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_verde_200);
            ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_selec_200);
            ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_cinza_200);
            ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_cinza_200);
        }

        else if(mViewPager.getCurrentItem() == 5 || mViewPager.getCurrentItem() == 6 || mViewPager.getCurrentItem() == 9){
            ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_checkbox_200);
            ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_verde_200);
            ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_checkbox_200);
            ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_verde_200);
            ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_selec_200);
        }
    }

}
