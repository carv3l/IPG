package house;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.Billboard;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SpotLight;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Text3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureCubeMap;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.media.j3d.WakeupOr;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

import appearance.MyMaterial;
import appearance.TextureAppearance;
import shapes.Table2;
import shapes.Bathroom;
import shapes.Bed;
import shapes.Car;
import shapes.Door;
import shapes.Floor;
import shapes.FloorLamp2;
import shapes.Kitchen;
import shapes.MyObj;
import shapes.Wall;
import shapes.couch;


public class house extends Frame implements MouseListener,ActionListener  {

	// Global bounds
	BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0),100);
	PointLight pLight = null; 
	PointLight pLight_r = null; 
	PointLight pLight_room = null;
	PointLight pLight_room_r = null;
	PickCanvas pc = null; // PickCanvas to perform picking
	KeyControl kc = null;
	


	int state = 0;
	
	public static int speed = 2500;


	public static void main(String[] args) {
		Frame frame = new house();
		frame.setPreferredSize(new Dimension(800, 900));
		frame.setTitle("HOUSE");
		frame.pack();
		frame.setVisible(true);

	}


	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}


	public house() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		cv.addMouseListener(this);
		
		
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		
		SimpleUniverse su = new SimpleUniverse(cv);
		
		
		BranchGroup bg = createSceneGraph();
		
		bg.compile();
		
		
		
		
		Transform3D viewTr = new Transform3D();
			
			viewTr.lookAt(new Point3d(-4.0, 1.5, 0.5), new Point3d(0.0, 0.0, 0.0), new Vector3d(0.0, 1.0, 0.0));
			viewTr.invert();
			
			//OrbitBehavior
			OrbitBehavior orbit = new OrbitBehavior(cv);
			orbit.setSchedulingBounds(bounds);
			su.getViewingPlatform().setViewPlatformBehavior(orbit);
			
			
	
		
		su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTr);
		su.addBranchGraph(bg);
				
		//PickCanvas
		pc = new PickCanvas(cv, bg);
		pc.setMode(PickTool.GEOMETRY);
		
		
	    
	    Panel panel = new Panel();
	    panel.setLayout(new GridLayout(5,1));
	    add(panel, BorderLayout.EAST);
	    Button button = new Button("Velocidade +");
	    button.addActionListener(this);
	    panel.add(button);
	    button = new Button("Velocidade -");
	    button.addActionListener(this);
	    panel.add(button);
	    
	    
		
	}
	

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		
		// Floor
		root.addChild(new Floor(10,-5 ,5, new Color3f(Color.DARK_GRAY), new Color3f(Color.LIGHT_GRAY), true));
		
		
		
		
		// Table
		TextureAppearance topApp = new TextureAppearance("images/wood2.jpg", false, this); 
		Appearance legApp = new Appearance();
		MyMaterial brass = new MyMaterial(MyMaterial.BRASS);
		legApp.setMaterial(brass);
		
		Appearance app = new Appearance();
		app.setMaterial(new Material());
		Table2 table = new Table2(topApp, legApp);

		Transform3D tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(-4.0f, 0f, -1f));
		TransformGroup tg = new TransformGroup(tr);
		tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		tg.addChild(table);
		root.addChild(tg);

		// Lamp

		TextureAppearance lampshadeApp  = new TextureAppearance("images/lampshade-texture.jpg", true, this); 
		Appearance lampApp = new Appearance();
		MyMaterial bronze = new MyMaterial(MyMaterial.BRONZE);
		lampApp.setMaterial(bronze);

		FloorLamp2 floorLamp = new FloorLamp2(lampshadeApp,lampApp);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(-1f, 0f, -2.5f));
		tg = new TransformGroup(tr);
		tg.addChild(floorLamp);
		root.addChild(tg);

		// Background
		Background background = new Background(new Color3f(Color.LIGHT_GRAY));
		background.setApplicationBounds(bounds);
		root.addChild(background);

	

		
		//Bed
		
		TextureAppearance  mattressApp  = new TextureAppearance("images/mattress-pattern.jpg", true, this); 
		legApp = new Appearance();
		legApp.setMaterial(brass);
		Bed bed = new Bed(topApp, legApp,mattressApp);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(2.8f, 0f, 2.5f)); //
		tg = new TransformGroup(tr);
		tg.addChild(bed);
		root.addChild(tg);
		
		
		//kitchen
		
		TextureAppearance  kitApp  = new TextureAppearance("images/laminatedkit.jpg", true, this); 
		TextureAppearance  tampoApp  = new TextureAppearance("images/wood.jpg", true, this); 
		
		legApp = new Appearance();
		legApp.setMaterial(brass);
		Kitchen kitchen = new Kitchen(kitApp, legApp,tampoApp);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(-90)));
		tr.setTranslation(new Vector3f(3.7f, 0f, -0.4f)); //
		tg = new TransformGroup(tr);
		tg.addChild(kitchen);
		root.addChild(tg);
		
		
		//Wall
		
		
		TextureAppearance wallApp = new TextureAppearance("images/brick_wall_texture.jpg", false, this);
		
		PolygonAttributes pa = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
			    PolygonAttributes.CULL_NONE,2);
			    wallApp.setPolygonAttributes(pa);
		
		Wall wall = new Wall(wallApp);
		tr = new Transform3D();
		tr.setScale(0.5f);
		tr.setTranslation(new Vector3f(4f, 0f, 0f)); //x ,Vertical,z
		tg = new TransformGroup(tr);
		tg.addChild(wall);
		root.addChild(tg);
		
		
		//Couch

				TextureAppearance  pillow1  = new TextureAppearance("images/gray.png", true, this); 
				TextureAppearance  pillow2  = new TextureAppearance("images/lightgray.png", true, this);
				TextureAppearance  pillow3  = new TextureAppearance("images/lightblue.jpg", true, this); 
				TextureAppearance  couchcolor  = new TextureAppearance("images/couchcolor.png", true, this); 
				
		couch couch = new couch(kitApp,legApp,pillow1,pillow2,pillow3,couchcolor);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(-3f, 0f, -2.9f)); //x ,Vertical,z
		tg = new TransformGroup(tr);
		tg.addChild(couch);
		root.addChild(tg);
				
		//Door BedRoom

		TextureAppearance  doorColorApp  = new TextureAppearance("images/door.jpg", true, this); 
		TextureAppearance  door_FrameApp = new TextureAppearance("images/darkbrown.png", true, this); 
		Door doorbed = new Door(door_FrameApp, doorColorApp);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(-2f,0f,1.9f)); //x ,Vertical,z
		tg = new TransformGroup(tr);
		tg.addChild(doorbed);
		root.addChild(tg);			

		Door doorbath = new Door(door_FrameApp, doorColorApp);
		tr = new Transform3D();
		tr.setScale(1f);
		tr.setTranslation(new Vector3f(-1.5f,0f,3.95f)); //x ,Vertical,z
		tr.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(180)));
		tg = new TransformGroup(tr);
		tg.addChild(doorbath);
		root.addChild(tg);
		
		
		Bathroom bathroom = new Bathroom();
		tr = new Transform3D();
		tr.setScale(0.3f);
		tr.setTranslation(new Vector3f(-3.5f,0.85f,1.1f)); //
		tr.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(161)));
		tg = new TransformGroup(tr);
		tg.addChild(bathroom);
		root.addChild(tg);

	
		
		AmbientLight aLight = new AmbientLight(true, new Color3f(Color.WHITE));
		aLight.setInfluencingBounds(bounds);
		root.addChild(aLight);
		
		//NEAR TABLE   0f, 3f, 0f
		
		////PointLight Bed Room

		pLight = new PointLight(new Color3f(Color.WHITE), new Point3f(0f, 3f, 3f), new Point3f(1f, 0f, 0f));
		pLight.setCapability(PointLight.ALLOW_STATE_READ);
		pLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		pLight.setInfluencingBounds(bounds);
		root.addChild(pLight);
		
		
		
		
		//PointLight Living Room
		pLight_room = new PointLight(new Color3f(Color.WHITE), new Point3f(-3f, 3f, -3f), new Point3f(1f, 0f, 0f));
		pLight_room.setCapability(PointLight.ALLOW_STATE_READ);
		pLight_room.setCapability(PointLight.ALLOW_STATE_WRITE);
		pLight_room.setInfluencingBounds(bounds);
		root.addChild(pLight_room);
		
		//PointLight BUT IN RED
		
		
		pLight_r = new PointLight(new Color3f(Color.RED), new Point3f(0f, 3f, 3f), new Point3f(1f, 0f, 0f));
		pLight_r.setCapability(PointLight.ALLOW_STATE_READ);
		pLight_r.setCapability(PointLight.ALLOW_STATE_WRITE);
		pLight_r.setInfluencingBounds(bounds);
		root.addChild(pLight_r);
		pLight_r.setEnable(false);
		
		
		pLight_room_r = new PointLight(new Color3f(Color.RED), new Point3f(-3f, 3f, -3f), new Point3f(1f, 0f, 0f));
		pLight_room_r.setCapability(PointLight.ALLOW_STATE_READ);
		pLight_room_r.setCapability(PointLight.ALLOW_STATE_WRITE);
		pLight_room_r.setInfluencingBounds(bounds);
		root.addChild(pLight_room_r);
		pLight_room_r.setEnable(false);
		
		
		// Billboards
		
		//Aparência da geometria de text e font
		Appearance appear = new Appearance();
		ColoringAttributes color = new ColoringAttributes(new Color3f(1f,1f,1f), ColoringAttributes.SHADE_GOURAUD);
		appear.setColoringAttributes(color);
		
		//geometria
		Font3D font = new Font3D(new Font("Agency FB",Font.BOLD, 1), new FontExtrusion());
		Text3D letra = new Text3D(font, "Quarto");
		Shape3D shape_letra = new Shape3D(letra,appear); 
		tr = new Transform3D();
		tr.setScale(0.05);
		
		
		//geometria
		Font3D fontcomic = new Font3D(new Font("Comic Sans MS",Font.BOLD, 1), new FontExtrusion());
		Text3D palavra_Banho = new Text3D(fontcomic, "Banho");
		Shape3D shape_banho = new Shape3D(palavra_Banho,appear); 
		tr = new Transform3D();
		tr.setScale(0.05);
			
		
		TransformGroup bbTg = new TransformGroup();
		bbTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(bbTg);

		
		Billboard bb = new Billboard(bbTg, Billboard.ROTATE_ABOUT_POINT, new Point3f(1f, 1f, 2f));
		bb.setSchedulingBounds(bounds);
		bbTg.addChild(bb);

		tr = new Transform3D();
		tr.setTranslation(new Vector3f(1f, 1f, 2f));
		tg = new TransformGroup(tr);
		tg.addChild(shape_letra);
		bbTg.addChild(tg);
		
		TransformGroup bbTg1 = new TransformGroup();
		bbTg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(bbTg1);
		
		Billboard bb_1 = new Billboard(bbTg1, Billboard.ROTATE_ABOUT_POINT, new Point3f(-4f, 1f, 2f));
		bb_1.setSchedulingBounds(bounds);
		bbTg1.addChild(bb_1);
		
		tr = new Transform3D();
		tr.setTranslation(new Vector3f(-4f, 1f, 2f));
		tg = new TransformGroup(tr);
		tg.addChild(shape_banho);
		bbTg1.addChild(tg);
		

		//Car

 		Appearance objApp = new Appearance();
		objApp.setMaterial(bronze);
		Car car = new Car();
		tr = new Transform3D();
		tr.setScale(0.1f);
		tr.setTranslation(new Vector3f(0f,0.102f,0f)); //
		tg = new TransformGroup(tr);
		tg.addChild(car);

		TransformGroup moveTg = new TransformGroup();
		moveTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		moveTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		moveTg.addChild(tg);
		root.addChild(moveTg);

		
	//	kc = new KeyControl(moveTg, car);

		kc = new KeyControl(moveTg, car);
		kc.setSchedulingBounds(bounds);
		root.addChild(kc);
				
		
		// Object
				Appearance MarioApp = new Appearance();
				MarioApp.setMaterial(new MyMaterial(MyMaterial.BRONZE));
				MyObj myObj = new MyObj(0.12f, MarioApp);
				// TransformGroup to move the object
				TransformGroup moveTgMario= new TransformGroup();
				moveTgMario.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				moveTgMario.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				root.addChild(moveTgMario);
				moveTgMario.addChild(myObj);
				
				
				 myObj = new MyObj(0.12f, lampApp);
				 TransformGroup moveTgLuigi= new TransformGroup();
				 moveTgLuigi.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				 moveTgLuigi.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				 root.addChild(moveTgLuigi);
				 moveTgLuigi.addChild(myObj);
				

				// Interpolator
				Point3f[] positions = new Point3f[9]; // Array of positions that thefine the path

				positions[0] = new Point3f(-2.3f, 0f, 4.8f);//Bathroom side

				positions[1] = new Point3f(-2.3f, 0f, 1f);//Bathroom side
				positions[2] = new Point3f(-2.3f, 0f, 1f);//Bathroom side

				positions[3] = new Point3f(-1.2f, 0f, 1f); //Bed Side
				positions[4] = new Point3f(-1.2f, 0f, 1f);//Bed Side

				positions[5] = new Point3f(-1.2f, 0f, 4.8f); //Bed Side
				positions[6] = new Point3f(-1.2f, 0f, 4.8f); //Bed Side

				positions[7] = new Point3f(-2.3f, 0f, 4.8f);//Bathroom side
				positions[8] = new Point3f(-2.3f, 0f, 4.8f);//Bathroom side
				
				
				
				Point3f[] luigi = new Point3f[11]; // Array of positions that thefine the path

				luigi[0] = new Point3f(-2.5f, 0f, -1.5f);//Hall

				luigi[1] = new Point3f(-1f, 0f, -2f);//Lamp side
				luigi[2] = new Point3f(-1f, 0f, -2f);//Lamp side
				
				luigi[3] = new Point3f(-0.5f, 0f, -0.5f);//kitchen side
				luigi[4] = new Point3f(-0.5f, 0f, -0.5f);//kitchen side
				
				
				luigi[5] = new Point3f(-1f, 0f, 0.5f);//Bed
				luigi[6] = new Point3f(-1f, 0f, 0.5f);//Bed
				
				
				luigi[7] = new Point3f(-2.5f, 0f, 0.5f); //Hall
				luigi[8] = new Point3f(-2.5f, 0f, 0.5f);//Hall


				luigi[9] = new Point3f(-2.5f, 0f, -1.5f); //Hall
				luigi[10] = new Point3f(-2.5f, 0f, -1.5f);//Hall
				
				

				Quat4f[] quats = new Quat4f[9]; 
				Quat4f q = new Quat4f();
				for (int i = 0; i < quats.length; i++)
					quats[i] = new Quat4f();

				// Create the orientation as a AxisAngle4f object and convert it to a quaternion
				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(0)));
				quats[0].add(q);

				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(0)));
				quats[1].add(q);
				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-90)));
				quats[2].add(q);

				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-90)));
				quats[3].add(q);
				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-180)));
				quats[4].add(q);

				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-180)));
				quats[5].add(q);
				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-270)));
				quats[6].add(q);

				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-270)));
				quats[7].add(q);
				q.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(0)));
				quats[8].add(q);
				
				
				Quat4f[] quatsLuigi = new Quat4f[11]; 
				Quat4f q1 = new Quat4f();
				for (int i = 0; i < quatsLuigi.length; i++)
					quatsLuigi[i] = new Quat4f();

				// Create the orientation as a AxisAngle4f object and convert it to a quaternion
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-70)));
				quatsLuigi[0].add(q1);

				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-70)));
				quatsLuigi[1].add(q1);
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-140)));
				quatsLuigi[2].add(q1);

				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-140)));
				quatsLuigi[3].add(q1);
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-220)));
				quatsLuigi[4].add(q1);

				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-220)));
				quatsLuigi[5].add(q1);
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-270)));
				quatsLuigi[6].add(q1);

				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-270)));
				quatsLuigi[7].add(q1);
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-360)));
				quatsLuigi[8].add(q1);
		
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-360)));
				quatsLuigi[9].add(q1);
				q1.set(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(-70)));
				quatsLuigi[10].add(q1);

				float knots[] = new float[9];

				knots[0] = 0f;
				knots[1] = 0.1f;
				knots[2] = 0.15f;
				knots[3] = 0.6f;
				knots[4] = 0.7f;
				knots[5] = 0.8f;
				knots[6] = 0.9f;
				knots[7] = 0.95f;
				knots[8] = 1f;
				
				float knotsLuigi[] = new float[11];
					knotsLuigi[0] = 0f; 
				 for (int i = 1; i < knotsLuigi.length; i++) knotsLuigi[i] = i * 0.1f;
					


				Alpha alphaluigi = new Alpha(-1, speed); //2500
				Alpha alpha = new Alpha(-1, 0, speed, 10000, 0, 0); //2500
				System.out.println("Speed:"+speed);

				tr = new Transform3D();
				// tr.rotX(Math.toRadians(45));
				RotPosPathInterpolator interpolatorMario = new RotPosPathInterpolator(alpha, moveTgMario, tr, knots, quats, positions);
				interpolatorMario.setSchedulingBounds(bounds);
				moveTgMario.addChild(interpolatorMario);
				
				RotPosPathInterpolator interpolatorLuigi = new RotPosPathInterpolator(alphaluigi, moveTgLuigi, tr, knotsLuigi, quatsLuigi,luigi);
				interpolatorLuigi.setSchedulingBounds(bounds);
				moveTgLuigi.addChild(interpolatorLuigi);
				
				//root.addChild(new Points(luigi, new Point3f(0f, 0.25f, 0f), new Color3f(Color.RED), 15f));

		return root;
		
	}
		

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Velocidade +".equals(cmd)) {
			speed -=200;
		} else if ("Velocidade -".equals(cmd)) {
			speed +=200;
		}
System.out.println("Speed"+speed);
		return;		
	}
	
	

public void mouseClicked(MouseEvent e) {
	pc.setShapeLocation(e);
	PickResult result = pc.pickClosest();

			// Verify if it is the Lampshade
		Shape3D nodeS = (Shape3D) result.getNode(PickResult.SHAPE3D);
		if (nodeS != null) {
			// System.out.println(nodeS.toString());
			if (nodeS.toString().contains("Lampshade")) {
				System.out.println("X");
				if (pLight.getEnable()) {
					pLight.setEnable(false);
					pLight_room.setEnable(false);}
				else {
					pLight.setEnable(true);
					pLight_room.setEnable(true);
				}
				return;
			}
		}
	}


@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub

}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub

}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub

}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

}


class KeyControl extends Behavior {
	private TransformGroup moveTg = null;
	private Node node = null;
	private boolean collision = false;
	PickCanvas pc = null; // PickCanvas to perform picking
	private WakeupCondition wakeupCondition = null;

	private int lastKey = 0;


	
	public KeyControl(TransformGroup moveTg, Node node) {
		// The constructor is used to pass to the behavior the objects that it needs.
		this.moveTg = moveTg;
		this.node = node;
	}

	@Override
	public void initialize() {
		
		WakeupCriterion[] keyEvents = new WakeupCriterion[4];
		keyEvents[0] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		keyEvents[1] = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
		keyEvents[2] = new WakeupOnCollisionEntry(node, WakeupOnCollisionEntry.USE_GEOMETRY);
		keyEvents[3] = new WakeupOnCollisionExit(node, WakeupOnCollisionExit.USE_GEOMETRY);

		// The wakeup condition is a combination of 4 wakeup criterion
		wakeupCondition = new WakeupOr(keyEvents);
		wakeupOn(wakeupCondition);
		

	}


	@Override
	public void processStimulus(Enumeration criteria) {
		WakeupCriterion wakeupCriterion;
		AWTEvent[] events;
		
		while (criteria.hasMoreElements()) {
			// Get each wakeup criterion
			wakeupCriterion = (WakeupCriterion) criteria.nextElement();

			// Find its type and process it
			if (wakeupCriterion instanceof WakeupOnAWTEvent) {
				events = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();

				for (int i = 0; i < events.length; i++) {
					if (events[i].getID() == KeyEvent.KEY_PRESSED) {
						keyPressed((KeyEvent) events[i]);
					} else if (events[i].getID() == KeyEvent.KEY_RELEASED) {
						// not implementes in this example
					}

				}
			} else if (wakeupCriterion instanceof WakeupOnCollisionEntry) {
				collision = true;
				state+=1;
				
				System.out.println("WakeupOnCollisionEntry");
				//System.out.println(wakeupCriterion.toString());
	
				for (int i = 0; i < 4; i++) {
					doTranslation(new Vector3f(0f, 0f, (float)(-0.05+0.01)));
				}
				
				if (state >1) {
//					pLight_room.setEnable(false);
					lights(1);
				}
				
			
				
			} else if (wakeupCriterion instanceof WakeupOnCollisionExit) {
				collision = false;
				System.out.println("WakeupOnCollisionExit");
			}
		}

		wakeupOn(wakeupCondition);
	}

	private void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();

		switch (keyCode) {

		case KeyEvent.VK_LEFT:
			if (!collision || (collision && lastKey != KeyEvent.VK_LEFT))
				// if (!collision)
				doRotationY(Math.toRadians(1.5));			
			break;
		case KeyEvent.VK_RIGHT:
			if (!collision || (collision && lastKey != KeyEvent.VK_RIGHT))
				// if (!collision)
				doRotationY(Math.toRadians(-1.5));
			break;
		case KeyEvent.VK_UP:
			if (!collision || (collision && lastKey != KeyEvent.VK_UP))
				// if (!collision)
				doTranslation(new Vector3f(0f, 0f, 0.05f));
			break;
		case KeyEvent.VK_DOWN:
			if (!collision || (collision && lastKey != KeyEvent.VK_DOWN))
				// if (!collision)
				doTranslation(new Vector3f(0f, 0f, -0.05f));
			break;
		}
		lastKey = keyCode;
	}
	private void lights(int i) {
		
		if (i == 1) {
			pLight_r.setEnable(true);
			pLight.setEnable(false);
			
			pLight_room_r.setEnable(true);
			pLight_room.setEnable(false);

		//JOptionPane.showMessageDialog(house.this,"GAME OVER");
			
		}
		
		
	}
	

	private void doRotationY(double t) {
		// Standard code to add a transformation to the actual transformation of a
		// TransformGroup
		

		// Create the new transformation to add
		Transform3D newTr = new Transform3D();
		
		newTr.rotY(t);
		
		// Get old transformation of the TransformGroup
		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);

		// Add the new transformation by multiplying the transformations
		oldTr.mul(newTr);

		// Set the new transformation
		moveTg.setTransform(oldTr);
	}

	private void doTranslation(Vector3f v) {
		Transform3D newTr = new Transform3D();
		newTr.setTranslation(v);

		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);

		oldTr.mul(newTr);

		moveTg.setTransform(oldTr);
	}

}



}



