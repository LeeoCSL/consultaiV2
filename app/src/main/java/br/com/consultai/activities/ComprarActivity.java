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
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFinalizadaFragment;
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFragment;
import br.com.consultai.model.Pagamento;

public class ComprarActivity extends AppCompatActivity {
   public static ImageView circulo1, circulo2, circulo3, traco1, traco2;

    public static ViewPager mViewPager;
    public static int selecaoBanco = 0;
//    Button testebtn;

    int i = 1;
    public static Pagamento pagamento = new Pagamento();

    private ComprarFragment mComprarFragment;
    private BandeiraFragment mBandeiraFragment;
    private SelecCreditoFragment mSelecCreditoFragment;
    private CadastrarCreditoFragment mCadastrarCreditoFragment;
    private PedidoFinalizadoCredFragment mPedidoFinalizadoCredFragment;
    private SelecBilheteFragment mSelecBilheteFragment;
    private CadastrarBilheteFragment mCadastrarBilheteFragment;
    private PedidoBoletoFragment mPedidoBoletoFragment;
    private PedidosFragment mPedidosFragment;
    private SelecBancoFragment mSelecBancoFragment;
    private TransferenciaFinalizadaFragment mTransferenciaFinalizadaFragment;
    private TransferenciaFragment mTransferenciaFragment;

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

        mViewPager = (ViewPager)findViewById(R.id.viewpager);

        mViewPager.setCurrentItem(0);
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
        viewPager.setAdapter(adapter);
    }

}
